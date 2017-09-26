package com.wavesplatform.generator

import cats.Show
import com.wavesplatform.generator.WideTransactionGenerator.Settings
import com.wavesplatform.generator.utils.Gen
import scorex.account.PrivateKeyAccount
import scorex.transaction.Transaction

class WideTransactionGenerator(settings: Settings,
                               accounts: Seq[PrivateKeyAccount]) extends TransactionGenerator {
  require(accounts.nonEmpty)

  override def next(): Iterator[Transaction] = {
    val limitedRecipientGen = Gen.address(settings.limitDestAccounts)
    Gen.txs(settings.minFee, settings.maxFee, accounts, limitedRecipientGen).take(settings.transactions)
  }

}

object WideTransactionGenerator {

  case class Settings(transactions: Int,
                      limitDestAccounts: Option[Int],
                      minFee: Long,
                      maxFee: Long) {
    require(transactions > 0)
    require(limitDestAccounts.forall(_ > 0))
  }

  object Settings {
    implicit val toPrintable: Show[Settings] = { x =>
      import x._
      s"""transactions per iteration: $transactions
         |number of recipients is ${limitDestAccounts.map(x => s"limited by $x").getOrElse("not limited")}
         |min fee: $minFee
         |max fee: $maxFee""".stripMargin
    }
  }

}