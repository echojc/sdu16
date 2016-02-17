import shapeless._
import org.scalacheck._
import org.scalacheck.Arbitrary._

object ArbitraryExample extends App {

  case class Foo(i: Int, s: String, b: Boolean)

  implicit val arbHNil: Arbitrary[HNil] = Arbitrary(Gen.const(HNil))

  implicit def arbHCons[H, T <: HList]
    (implicit arbH: Arbitrary[H], arbT: Arbitrary[T]): Arbitrary[H :: T] =
    Arbitrary {
      for {
        h ← arbH.arbitrary
        t ← arbT.arbitrary
      } yield h :: t
    }

  implicit def arbCaseClass[A, L <: HList]
    (implicit gen: Generic.Aux[A, L], arbL: Arbitrary[L]): Arbitrary[A] =
    Arbitrary {
      for {
        l ← arbL.arbitrary
      } yield gen.from(l)
    }

  val arbFoo = arbitrary[Foo]

  println(arbFoo.sample)

}
