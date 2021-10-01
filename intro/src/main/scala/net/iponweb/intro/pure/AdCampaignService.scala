package net.iponweb.intro.pure

import net.iponweb.intro.{Campaign, CampaignParams, Wallet}

class AdCampaignService {

  def launchCampaign(wallet: Wallet, params: CampaignParams): (Campaign, Charge) = {
    val price = priceAlgorithm(params)

    val campaign = Campaign(price, params)
    val charge = Charge(wallet, campaign.price)
    (campaign, charge)
  }

  def launchCampaigns(wallet: Wallet, params: List[CampaignParams]): (List[Campaign], Charge) = {
    val launches = params.map(launchCampaign(wallet, _))

    val (campaigns, charges) = launches.unzip
    (campaigns, charges.reduce(_ combine _))
  }

  private def priceAlgorithm(params: CampaignParams): Int = ???
}
