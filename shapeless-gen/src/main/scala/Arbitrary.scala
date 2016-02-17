import shapeless._
import org.scalacheck._
import org.scalacheck.Arbitrary._

object ArbitraryExample extends App {

  case class Foo(i: Int, s: String, b: Boolean)

  implicit val arbString: Arbitrary[String] = Arbitrary(Gen.alphaStr)

  implicit val arbHNil: Arbitrary[HNil] = Arbitrary(Gen.const(HNil))

  implicit def arbHCons[H : Arbitrary, T <: HList : Arbitrary]: Arbitrary[H :: T] =
    Arbitrary {
      for {
        h ← arbitrary[H]
        t ← arbitrary[T]
      } yield h :: t
    }

  implicit def arbCaseClass[A, L <: HList](implicit
    gen: Generic.Aux[A, L],
    arb: Arbitrary[L]
  ): Arbitrary[A] =
    Arbitrary {
      for {
        l ← arbitrary[L]
      } yield gen.from(l)
    }

  val arbFoo = arbitrary[Foo]

  println(arbFoo.sample)

}
