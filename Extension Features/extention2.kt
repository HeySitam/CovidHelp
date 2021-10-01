
/*
 * Copyright 2010-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package test.io

import test.assertArrayContentEquals
import java.io.*
import kotlin.io.walkTopDown
import kotlin.random.Random
import kotlin.test.*

class FilesTest {

    private val isCaseInsensitiveFileSystem = File("C:/") == File("c:/")
    private val isBackslashSeparator = File.separatorChar == '\\'


    @Suppress("DEPRECATION")
    @Test fun testPath() {
        val fileSuf = System.currentTimeMillis().toString()
        val file1 = createTempFile("temp", fileSuf)
        assertTrue(file1.path.endsWith(fileSuf), file1.path)
    }

    @Suppress("DEPRECATION")
    @Test fun testCreateTempDir() {
        val dirSuf = System.currentTimeMillis().toString()
        val dir1 = createTempDir("temp", dirSuf)
        assertTrue(dir1.exists() && dir1.isDirectory && dir1.name.startsWith("temp") && dir1.name.endsWith(dirSuf))
        assertFailsWith(IllegalArgumentException::class) {
            createTempDir("a")
        }

        val dir2 = createTempDir("temp")
        assertTrue(dir2.exists() && dir2.isDirectory && dir2.name.startsWith("temp") && dir2.name.endsWith(".tmp"))

        val dir3 = createTempDir()
        assertTrue(dir3.exists() && dir3.isDirectory && dir3.name.startsWith("tmp") && dir3.name.endsWith(".tmp"))

        dir1.delete()
        dir2.delete()
        dir3.delete()
    }

    @Suppress("DEPRECATION")
    @Test fun testCreateTempFile() {
        val fileSuf = System.currentTimeMillis().toString()
        val file1 = createTempFile("temp", fileSuf)
        assertTrue(file1.exists() && file1.name.startsWith("temp") && file1.name.endsWith(fileSuf))
        assertFailsWith(IllegalArgumentException::class) {
            createTempFile("a")
        }

        val file2 = createTempFile("temp")
        assertTrue(file2.exists() && file2.name.startsWith("temp") && file2.name.endsWith(".tmp"))

        val file3 = createTempFile()
        assertTrue(file3.exists() && file3.name.startsWith("tmp") && file3.name.endsWith(".tmp"))

        file1.delete()
        file2.delete()
        file3.delete()
    }

    @Suppress("DEPRECATION")
    @Test fun listFilesWithFilter() {
        val dir = createTempDir("temp")

        createTempFile("temp1", ".kt", dir)
        createTempFile("temp2", ".java", dir)
        createTempFile("temp3", ".kt", dir)

        // This line works only with Kotlin File.listFiles(filter)
        val result = dir.listFiles { it -> it.name.endsWith(".kt") } // todo ambiguity on SAM
        assertEquals(2, result!!.size)
        // This line works both with Kotlin File.listFiles(filter) and the same Java function because of SAM
        val result2 = dir.listFiles { it -> it.name.endsWith(".kt") }
        assertEquals(2, result2!!.size)
    }

    @Test fun relativeToRooted() {
        val file1 = File("/foo/bar/baz")
        val file2 = File("/foo/baa/ghoo")

        assertEquals("../../bar/baz", file1.relativeTo(file2).invariantSeparatorsPath)

        val file3 = File("/foo/bar")

        assertEquals("baz", file1.toRelativeString(file3))
        assertEquals("..", file3.toRelativeString(file1))

        val file4 = File("/foo/bar/")

        assertEquals("baz", file1.toRelativeString(file4))
        assertEquals("..", file4.toRelativeString(file1))
        assertEquals("", file3.toRelativeString(file4))
        assertEquals("", file4.toRelativeString(file3))

        val file5 = File("/foo/baran")

        assertEquals("../bar", file3.relativeTo(file5).invariantSeparatorsPath)
        assertEquals("../baran", file5.relativeTo(file3).invariantSeparatorsPath)
        assertEquals("../bar", file4.relativeTo(file5).invariantSeparatorsPath)
        assertEquals("../baran", file5.relativeTo(file4).invariantSeparatorsPath)

        if (isBackslashSeparator) {
            val file6 = File("C:\\Users\\Me")
            val file7 = File("C:\\Users\\Me\\Documents")

            assertEquals("..", file6.toRelativeString(file7))
            assertEquals("Documents", file7.toRelativeString(file6))

            val file8 = File("""\\my.host\home/user/documents/vip""")
            val file9 = File("""\\my.host\home/other/images/nice""")

            assertEquals("../../../user/documents/vip", file8.relativeTo(file9).invariantSeparatorsPath)
            assertEquals("../../../other/images/nice", file9.relativeTo(file8).invariantSeparatorsPath)
        }

        if (isCaseInsensitiveFileSystem) {
            assertEquals("bar", File("C:/bar").toRelativeString(File("c:/")))
        }
    }

    @Test fun relativeToRelative() {
        val nested = File("foo/bar")
        val base = File("foo")

        assertEquals("bar", nested.toRelativeString(base))
        assertEquals("..", base.toRelativeString(nested))

        val empty = File("")
        val current = File(".")
        val parent = File("..")
        val outOfRoot = File("../bar")

        assertEquals(File("../bar"), outOfRoot.relativeTo(empty))
        assertEquals(File("../../bar"), outOfRoot.relativeTo(base))
        assertEquals("bar", outOfRoot.toRelativeString(parent))
        assertEquals("..", parent.toRelativeString(outOfRoot))

        val root = File("/root")
        val files = listOf(nested, base, empty, outOfRoot, current, parent)
        val bases = listOf(nested, base, empty, current)

        for (file in files)
            assertEquals("", file.toRelativeString(file), "file should have empty path relative to itself: $file")

        for (file in files) {
            @Suppress("NAME_SHADOWING")
            for (base in bases) {
                val rootedFile = root.resolve(file)
                val rootedBase = root.resolve(base)
                assertEquals(file.relativeTo(base), rootedFile.relativeTo(rootedBase), "nested: $file, base: $base")
                assertEquals(file.toRelativeString(base), rootedFile.toRelativeString(rootedBase), "strings, nested: $file, base: $base")
            }
        }
    }

    @Test fun relativeToFails() {
        val absolute = File("/foo/bar/baz")
        val relative = File("foo/bar")
        val networkShare1 = File("""\\my.host\share1/folder""")
        val networkShare2 = File("""\\my.host\share2\folder""")

        fun assertFailsRelativeTo(file: File, base: File) {
            val e = assertFailsWith<IllegalArgumentException>("file: $file, base: $base") { file.relativeTo(base) }
            val message = assertNotNull(e.message)
            assert(file.toString() in message)
            assert(base.toString() in message)
        }

        val allFiles = listOf(absolute, relative) + if (isBackslashSeparator) listOf(networkShare1, networkShare2) else emptyList()
        for (file in allFiles) {
            for (base in allFiles) {
                if (file != base) assertFailsRelativeTo(file, base)
            }
        }

        assertFailsRelativeTo(File("y"), File("../x"))

        if (isBackslashSeparator) {
            val fileOnC = File("C:/dir1")
            val fileOnD = File("D:/dir2")
            assertFailsRelativeTo(fileOnC, fileOnD)
        }
    }

    @Test fun relativeTo() {
        assertEquals("kotlin", File("src/kotlin").toRelativeString(File("src")))
        assertEquals("", File("dir").toRelativeString(File("dir")))
        assertEquals("..", File("dir").toRelativeString(File("dir/subdir")))
        assertEquals(File("../../test"), File("test").relativeTo(File("dir/dir")))
    }

    @Suppress("INVISIBLE_MEMBER")
    private fun checkFilePathComponents(f: File, root: File, elements: List<String>) {
        assertEquals(root, f.root)
        val components = f.toComponents()
        assertEquals(root, components.root)
        assertEquals(elements, components.segments.map { it.toString() })
    }

    @Test fun filePathComponents() {
        checkFilePathComponents(File("/foo/bar"), File("/"), listOf("foo", "bar"))
        checkFilePathComponents(File("/foo/bar/gav"), File("/"), listOf("foo", "bar", "gav"))
        checkFilePathComponents(File("/foo/bar/gav/"), File("/"), listOf("foo", "bar", "gav"))
        checkFilePathComponents(File("bar/gav"), File(""), listOf("bar", "gav"))
        checkFilePathComponents(File("C:/bar/gav"), File("C:/"), listOf("bar", "gav"))
        checkFilePathComponents(File("C:/"), File("C:/"), listOf())
        checkFilePathComponents(File("C:"), File("C:"), listOf())
        if (isBackslashSeparator) {
            // Check only in Windows
            checkFilePathComponents(File("\\\\host.ru\\home\\mike"), File("\\\\host.ru\\home"), listOf("mike"))
            checkFilePathComponents(File("//host.ru/home/mike"), File("//host.ru/home"), listOf("mike"))
            checkFilePathComponents(File("\\foo\\bar"), File("\\"), listOf("foo", "bar"))
            checkFilePathComponents(File("C:\\bar\\gav"), File("C:\\"), listOf("bar", "gav"))
            checkFilePathComponents(File("C:\\"), File("C:\\"), listOf())
        }
        checkFilePathComponents(File(""), File(""), listOf())
        checkFilePathComponents(File("."), File(""), listOf("."))
        checkFilePathComponents(File(".."), File(""), listOf(".."))
    }

    @Test fun fileRoot() {
        val rooted = File("/foo/bar")
        assertTrue(rooted.isRooted)
//        assertEquals("/", rooted.root.invariantSeparatorsPath)

        if (isBackslashSeparator) {
            val diskRooted = File("""C:\foo\bar""")
            assertTrue(diskRooted.isRooted)
//            assertEquals("""C:\""", diskRooted.rootName)

            val networkRooted = File("""\\network\share\""")
            assertTrue(networkRooted.isRooted)
//            assertEquals("""\\network\share""", networkRooted.rootName)
        }
        val relative = File("foo/bar")
        assertFalse(relative.isRooted)
//        assertEquals("", relative.rootName)
    }
