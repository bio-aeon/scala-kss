package net.iponweb.intro

final case class CampaignParams(name: String, brand: String, city: String)

final case class Campaign(price: Int, params: CampaignParams)
