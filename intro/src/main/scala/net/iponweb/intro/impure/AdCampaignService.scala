package net.iponweb.intro.impure

import net.iponweb.intro.{Campaign, CampaignParams, Wallet}

class AdCampaignService {

  def launchCampaign(wallet: Wallet,
                     params: CampaignParams,
                     paymentService: PaymentService): Campaign = {
    val price = priceAlgorithm(params)

    val campaign = Campaign(price, params)

    paymentService.charge(wallet, campaign.price)

    campaign
  }

  private def priceAlgorithm(params: CampaignParams): Int = ???
}
