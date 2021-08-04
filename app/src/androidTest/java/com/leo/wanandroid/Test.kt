package com.moyoi.gamebox

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


/**
 * @Description 单元测试
 * @Author Lu
 * @Date 2021/7/15 11:55
 * @Version: 1.0
 */
@RunWith(MockitoJUnitRunner::class)
class Test {
    val TEST_STRING = "Hello Android Unit Test."

    lateinit var viewModel:MainViewModel

    @Before
    @Throws(Exception::class)
    fun setUp() {
        viewModel = MainViewModel()
    }

    @Test
    @Throws(Exception::class)
    fun testNetWorkTime()  {
        viewModel.getUnReadMessageNum()
        viewModel.numResult.value?.let {
            assertEquals("0",it.data)
        }

    }
}