package com.example.feature.photo.gallery.impl.domain

import com.example.core.test.RxJavaTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Test for [CalculateGridParamsUseCase]
 */
class CalculateGridParamsUseCaseTest {
    @Rule
    @JvmField
    val rxRule = RxJavaTestRule()

    private lateinit var useCase: CalculateGridParamsUseCase

    @Before
    fun setUp() {
        useCase = CalculateGridParamsUseCase()
    }

    @Test
    fun `test calculation`() {
        // prepare
        val params = CalculateGridParamsUseCase.Params(10, 8)
        val expected = CalculateGridParamsUseCase.Result(2, 5)

        // do
        val testObserver = useCase(params).test()

        // verify
        testObserver
            .assertNoErrors()
            .assertValue(expected)
    }
}