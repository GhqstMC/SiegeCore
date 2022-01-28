package net.siegerpg.siege.shops.shop

import net.siegerpg.siege.core.items.implemented.misc.materials.FishingExplanation
import net.siegerpg.siege.core.items.implemented.misc.materials.drops.blocks.*
import net.siegerpg.siege.core.items.implemented.misc.materials.drops.mobs.Bone
import net.siegerpg.siege.core.items.implemented.misc.tools.fishingRod.*
import net.siegerpg.siege.shops.Shop
import net.siegerpg.siege.shops.ShopItem

class Mary : Shop() {

	override var name : String = "Mary"
	override var permission : String = "siege.shops.shop.mary"
	override var items : List<ShopItem> = listOf(
			//RODS
			ShopItem(OldRod(-1), 1000, hashMapOf(), false) {
				OldRod().getUpdatedItem(false)
			},
			ShopItem(
					OakRod(-1), 40000, hashMapOf(
					Vine() to 128,
					Stick() to 196
					                            ), true
			        ) {
				OakRod(0).getUpdatedItem(false)
			},
			ShopItem(
					BoneRod(-1), 70000, hashMapOf(
					Vine() to 128,
					Bone() to 196
					                             ), true
			        ) {
				BoneRod(0).getUpdatedItem(false)
			},
			ShopItem(FishingExplanation(-1), -1, hashMapOf(), false) {
				FishingExplanation(-1).getUpdatedItem(false)
			},
			ShopItem(
					MetalRod(-1), 110000, hashMapOf(
					Chain() to 196,
					MetalScrap() to 128
					                               ), true
			        ) {
				MetalRod(0).getUpdatedItem(false)
			},
			ShopItem(
					RefinedRod(-1), 220000, hashMapOf(
					Chain() to 128,
					RefinedMetal() to 256
					                                 ), true
			        ) {
				RefinedRod(0).getUpdatedItem(false)
			},
			ShopItem(
					TitaniumRod(-1), 500000, hashMapOf(
					Chain() to 128,
					Titanium() to 256
					                                  ), true
			        ) {
				TitaniumRod(0).getUpdatedItem(false)
			}
	                                            )
}