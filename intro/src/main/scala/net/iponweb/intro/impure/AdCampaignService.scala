package net.iponweb.intro.impure

import net.iponweb.intro.{Campaign, CampaignParams, Wallet}

class AdCampaignService {

  def launchCampaign(wallet: Wallet, params: CampaignParams): Campaign = {
    val price = 100 // some algorithm(params) -> returns 100

    val campaign = Campaign(price, params)

    wallet.charge(campaign.price)

    campaign
  }
}
