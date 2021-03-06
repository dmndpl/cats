package alleycats

import cats.SemigroupK
import simulacrum.typeclass
import scala.annotation.implicitNotFound

@implicitNotFound("Could not find an instance of ConsK for ${F}")
@typeclass trait ConsK[F[_]] extends Serializable {
  def cons[A](hd: A, tl: F[A]): F[A]
}

object ConsK {
  implicit def pureSemigroupKIsConsK[F[_]](implicit p: Pure[F], s: SemigroupK[F]): ConsK[F] =
    new ConsK[F] {
      def cons[A](hd: A, tl: F[A]): F[A] = s.combineK(p.pure(hd), tl)
    }

  /****************************************************************************/
  /* THE FOLLOWING CODE IS MANAGED BY SIMULACRUM; PLEASE DO NOT EDIT!!!!      */
  /****************************************************************************/
  /**
   * Summon an instance of [[ConsK]] for `F`.
   */
  @inline def apply[F[_]](implicit instance: ConsK[F]): ConsK[F] = instance

  trait Ops[F[_], A] {
    type TypeClassType <: ConsK[F]
    def self: F[A]
    val typeClassInstance: TypeClassType
  }
  trait AllOps[F[_], A] extends Ops[F, A]
  trait ToConsKOps {
    implicit def toConsKOps[F[_], A](target: F[A])(implicit tc: ConsK[F]): Ops[F, A] {
      type TypeClassType = ConsK[F]
    } = new Ops[F, A] {
      type TypeClassType = ConsK[F]
      val self: F[A] = target
      val typeClassInstance: TypeClassType = tc
    }
  }
  object nonInheritedOps extends ToConsKOps
  object ops {
    implicit def toAllConsKOps[F[_], A](target: F[A])(implicit tc: ConsK[F]): AllOps[F, A] {
      type TypeClassType = ConsK[F]
    } = new AllOps[F, A] {
      type TypeClassType = ConsK[F]
      val self: F[A] = target
      val typeClassInstance: TypeClassType = tc
    }
  }

  /****************************************************************************/
  /* END OF SIMULACRUM-MANAGED CODE                                           */
  /****************************************************************************/

}
