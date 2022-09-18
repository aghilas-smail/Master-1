package mandelbrot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ComplexTest {
    private Complex onePlusI;
    private Complex minusI;
    private Complex oneMinusI;
    private Complex twoI;
    private Complex two;
    private Complex one;
    private Complex i;
    private Complex zero;

    @BeforeEach
    void initializeTestValues(){
        onePlusI = new Complex(1,1);
        minusI = new Complex(0,-1);
        oneMinusI = new Complex(1, -1);
        twoI = new Complex(0,2);
        two = new Complex(2,0);
        one = new Complex(1,0);
        i = new Complex(0,1);
        zero = new Complex(0,0);
    }

    @Test
    void testEquals(){
        assertThat(onePlusI).isEqualTo(onePlusI);
        assertThat(onePlusI).isEqualTo(new Complex(1, 1));
        assertThat(onePlusI).isNotEqualTo(oneMinusI);
        assertThat(two).isNotEqualTo(twoI);
    }

    @Test
    void testGetReal(){
        assertThat(two.getReal()).isCloseTo(2., within(Helpers.EPSILON));
        assertThat(onePlusI.getReal()).isCloseTo(1., within(Helpers.EPSILON));
        assertThat(oneMinusI.getReal()).isCloseTo(1., within(Helpers.EPSILON));
        assertThat(twoI.getReal()).isCloseTo(0.,within(Helpers.EPSILON));
    }

    @Test
    void testGetImaginary(){
        assertThat(two.getImaginary()).isCloseTo(0., within(Helpers.EPSILON));
        assertThat(onePlusI.getImaginary()).isCloseTo(1., within(Helpers.EPSILON));
        assertThat(oneMinusI.getImaginary()).isCloseTo(-1., within(Helpers.EPSILON));
    }

    @Test
    void testOne(){
        assertThat(Complex.ONE.getReal()).isCloseTo(1., within(Helpers.EPSILON));
        assertThat(Complex.ONE.getImaginary()).isCloseTo(0., within(Helpers.EPSILON));
    }

    @Test
    void testI(){
        assertThat(Complex.I.getReal()).isCloseTo(0., within(Helpers.EPSILON));
        assertThat(Complex.I.getImaginary()).isCloseTo(1., within(Helpers.EPSILON));
    }
    @Test
    void testZero(){
        assertThat(Complex.ZERO.getReal()).isCloseTo(0., within(Helpers.EPSILON));
        assertThat(Complex.ZERO.getImaginary()).isCloseTo(0., within(Helpers.EPSILON));
    }
    @Test
    void testNegate(){
        assertThat(two.negate()).isEqualTo(new Complex(-2,0));
        assertThat(minusI.negate()).isEqualTo(i);
        assertThat(oneMinusI.negate()).isEqualTo(new Complex(-1, 1));
    }

    @Test
    void testReciprocal(){
        assertThat(one.reciprocal()).isEqualTo(one);
        assertThat(minusI.reciprocal()).isEqualTo(i);
        assertThat(two.reciprocal()).isEqualTo(new Complex(0.5,0));
        assertThat(oneMinusI.reciprocal()).isEqualTo(new Complex(0.5,0.5));
    }

    @Test
    void testReciprocalOfZero(){
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(()->zero.reciprocal());
    }

    @Test
    void testDivide(){
        assertThat(onePlusI.divide(Complex.ONE)).isEqualTo(onePlusI);
        assertThat(Complex.ONE.divide(two)).isEqualTo(new Complex(0.5, 0));
        assertThat(oneMinusI.divide(onePlusI)).isEqualTo(minusI);
    }

    @Test
    void testDivideByZero(){
        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(()->one.divide(zero));
    }

    @Test
    void testConjugate(){
        assertThat(two.conjugate()).isEqualTo(two);
        assertThat(oneMinusI.conjugate()).isEqualTo(onePlusI);
    }

    @Test
    void testRotation(){
        assertThat(Complex.rotation(Math.PI/2)).isEqualTo(i);
        assertThat(Complex.rotation(-Math.PI/2)).isEqualTo(minusI);
        assertThat(Complex.rotation(0)).isEqualTo(one);
        assertThat(Complex.rotation(Math.PI/4)).isEqualTo(new Complex(Math.sqrt(2)/2., Math.sqrt(2)/2.));
        assertThat(Complex.rotation(Math.PI/3)).isEqualTo(new Complex(1./2., Math.sqrt(3)/2.));
    }

    @Test
    void testBasicToString(){
        assertThat(two.toString()).contains("2.0");
        assertThat(i.toString()).contains("i");
    }

    @Test
    void testToStringFormat(){
        assertThat(oneMinusI.toString()).isEqualTo("1.0 - 1.0i");
        assertThat(onePlusI.toString()).isEqualTo("1.0 + 1.0i");
        assertThat(minusI.toString()).isEqualTo("-1.0i");
        assertThat(twoI.toString()).isEqualTo("2.0i");
        assertThat(two.toString()).isEqualTo("2.0");
    }

    @Test
    void testAdd(){
        assertThat(i.add(i)).isEqualTo(twoI);
        assertThat(one.add(i)).isEqualTo(onePlusI);
        assertThat(one.add(minusI)).isEqualTo(oneMinusI);
        assertThat(i.add(minusI)).isEqualTo(zero);
        assertThat(onePlusI.add(oneMinusI)).isEqualTo(two);
    }

}