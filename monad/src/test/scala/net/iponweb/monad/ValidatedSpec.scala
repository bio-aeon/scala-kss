package net.iponweb.monad

import net.iponweb.monad.instances.list._
import net.iponweb.monad.instances.validated._
import org.specs2.mutable.Specification

class ValidatedSpec extends Specification {

  case class Address(city: String, postalCode: String, countryCode: String)

  class AddressValidator(implicit F: Applicative[Validated[List[String], *]]) {

    def validate(address: Address): Validated[List[String], Address] =
      F.map3(
        validateCity(address.city),
        validatePostalCode(address.postalCode),
        validateCountryCode(address.countryCode)
      )(Address.apply _)

    private def validateCity(city: String): Validated[List[String], String] =
      if (city.isEmpty) {
        Validated.invalid(List("Empty address"))
      } else {
        Validated.valid(city)
      }

    private def validatePostalCode(postalCode: String): Validated[List[String], String] =
      if (postalCode.length == 6) {
        Validated.valid(postalCode)
      } else {
        Validated.invalid(List("Incorrect postal code"))
      }

    private def validateCountryCode(countryCode: String): Validated[List[String], String] =
      if (countryCode.length == 2) {
        Validated.valid(countryCode)
      } else {
        Validated.invalid(List("Incorrect country code"))
      }
  }

  val validator = new AddressValidator()

  "Validated should" >> {

    "return success for valid address" >> {
      val address = Address("Moscow", "111111", "RU")

      validator.validate(address) must beLike {
        case Validated.Valid(x) => x mustEqual address
      }
    }

    "return collected errors for invalid address" >> {
      val address = Address("", "1000000000", "SOME")

      validator.validate(address) must beLike {
        case Validated.Invalid(e) =>
          e must containTheSameElementsAs(
            List("Empty address", "Incorrect postal code", "Incorrect country code")
          )
      }
    }
  }
}
