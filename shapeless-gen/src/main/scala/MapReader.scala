import shapeless._
import org.scalacheck._
import org.scalacheck.Arbitrary._

trait MapReader[T] {
  type K
  def read(map: Map[Any, Any]): T
}

object MapReader {
  type Aux[T, K0] = MapReader[T] { type K = K0 }

  implicit def mrInt[K0](implicit wk: Witness.Aux[K0]): MapReader.Aux[Int, K0] =
    new MapReader[Int] {
      type K = K0
      def read(map: Map[Any, Any]): Int =
        map(wk.value).asInstanceOf[Int]
    }

  implicit def mrString[K0](implicit wk: Witness.Aux[K0]): MapReader.Aux[String, K0] =
    new MapReader[String] {
      type K = K0
      def read(map: Map[Any, Any]): String =
        map(wk.value).asInstanceOf[String]
    }

  implicit def mrBoolean[K0](implicit wk: Witness.Aux[K0]): MapReader.Aux[Boolean, K0] =
    new MapReader[Boolean] {
      type K = K0
      def read(map: Map[Any, Any]): Boolean =
        map(wk.value).asInstanceOf[Boolean]
    }

  implicit val mrHNil: MapReader.Aux[HNil, HNil] =
    new MapReader[HNil] {
      type K = HNil
      def read(map: Map[Any, Any]): HNil = HNil
    }

  implicit def mrHCons[K0, J <: HList, H, T <: HList](implicit
    mrH: MapReader.Aux[H, K0],
    mrT: MapReader.Aux[T, J]
  ): MapReader.Aux[H :: T, K0 :: J] =
    new MapReader[H :: T] {
      type K = K0 :: J
      def read(map: Map[Any, Any]): H :: T =
        mrH.read(map) :: mrT.read(map)
    }

  implicit def mrCaseClass[A, K0 <: HList, L <: HList](implicit
    lab: DefaultSymbolicLabelling.Aux[A, K0],
    gen: Generic.Aux[A, L],
    mr: MapReader.Aux[L, K0]
  ): MapReader.Aux[A, K0] =
    new MapReader[A] {
      type K = K0
      def read(map: Map[Any, Any]): A =
        gen.from(mr.read(map))
    }
}

object MapReaderExample extends App {

  case class Foo(i: Int, s: String, b: Boolean)

  val mrFoo = implicitly[MapReader[Foo]]

  println(mrFoo.read(Map('i → 1, 's → "foo", 'b → true)))
}
