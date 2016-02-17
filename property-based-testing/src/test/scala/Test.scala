import org.scalacheck._
import org.scalacheck.Arbitrary._
import org.scalacheck.Gen._
import org.scalatest._
import org.scalatest.prop._

class Test extends WordSpec with Matchers with GeneratorDrivenPropertyChecks {
  import Main._

  "fast inverse square root" should {
    "approximate 1/sqrt(2) within 0.01f" in {
      fastInvSqrt(2) should be ((1/Math.sqrt(2)) +- 0.01)
    }
















    //"approximate 1/sqrt(pi) within 0.01f" in {
    //  val pi = 3.1416f
    //  fastInvSqrt(pi) should be ((1/Math.sqrt(pi)) +- 0.01)
    //}


















    //"approximate 1/sqrt(3) within 0.01f" in {
    //  fastInvSqrt(3) should be ((1/Math.sqrt(3)) +- 0.01)
    //}
















    //"approximate 1/sqrt(n) within 0.01f" in {
    //  forAll(choose(0f, 360f)) { n: Float ⇒
    //    fastInvSqrt(n) should be ((1/Math.sqrt(n)) +- 0.01)
    //  }
    //}
  }
}
