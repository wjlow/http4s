package org.http4s
package syntax

import cats._
import cats.data.{Kleisli, OptionT}

trait KleisliResponseSyntax {
  implicit def http4sServiceSyntax[F[_]: Functor, A](
      service: Kleisli[OptionT[F, ?], A, Response[F]]): KleisliResponseOps[F, A] =
    new KleisliResponseOps[F, A](service)
}

final class KleisliResponseOps[F[_]: Functor, A](self: Kleisli[OptionT[F, ?], A, Response[F]]) {
  def orNotFound: Kleisli[F, A, Response[F]] =
    Kleisli(a => self.run(a).getOrElse(Response[F](Status.NotFound)))
}
