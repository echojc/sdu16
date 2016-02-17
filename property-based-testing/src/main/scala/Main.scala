object Main extends App {

  // https://en.wikipedia.org/wiki/Fast_inverse_square_root
  def fastInvSqrt(n: Float): Double = {
    import java.lang.Float._

    val bits = floatToIntBits(n)
    val wat = intBitsToFloat(0x5f3759df - (bits >> 1))

    wat
  }
}

















// refine with newton-raphson
//wat * (1.5f - (0.5f * n * wat * wat))
