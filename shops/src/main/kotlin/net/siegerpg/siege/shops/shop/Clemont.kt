package net.siegerpg.siege.shops.shop

import net.siegerpg.siege.core.items.implemented.misc.materials.GRAYFILLER
import net.siegerpg.siege.core.items.implemented.misc.tools.*
import net.siegerpg.siege.shops.Shop
import net.siegerpg.siege.shops.ShopItem

class Clemont : Shop() {

	override var name : String = "Clemont"
	override var permission : String = "siege.shops.shop.clemont"
	override var items : List<ShopItem> = listOf(
			//LUCKY TOOLS
			ShopItem(HammerAndChisel(100), 100000, hashMapOf(), false) {
				HammerAndChisel(100).getUpdatedItem(false)
			},
			ShopItem(Trowel(100), 100000, hashMapOf(), false) {
				Trowel(100).getUpdatedItem(false)
			},
			ShopItem(Handsaw(100), 100000, hashMapOf(), false) {
				Handsaw(100).getUpdatedItem(false)
			},
			ShopItem(GRAYFILLER(-1), -1, hashMapOf(), false) {
				GRAYFILLER(-1).getUpdatedItem(false)
			},
			ShopItem(GlowingHammerAndChisel(100), 125000, hashMapOf(), false) {
				GlowingHammerAndChisel(100).getUpdatedItem(false)
			},
			ShopItem(GlowingTrowel(100), 125000, hashMapOf(), false) {
				GlowingTrowel(100).getUpdatedItem(false)
			},
			ShopItem(GlowingHandsaw(100), 125000, hashMapOf(), false) {
				GlowingHandsaw(100).getUpdatedItem(false)
			},

			//STEEL
			ShopItem(SteelPickaxe(100), 175000, hashMapOf(), false) {
				SteelPickaxe(100).getUpdatedItem(false)
			},
			ShopItem(SteelShovel(100), 175000, hashMapOf(), false) {
				SteelShovel(100).getUpdatedItem(false)
			},
			ShopItem(SteelAxe(100), 175000, hashMapOf(), false) {
				Handsaw(100).getUpdatedItem(false)
			},
			ShopItem(GRAYFILLER(-1), -1, hashMapOf(), false) {
				GRAYFILLER(-1).getUpdatedItem(false)
			},
			ShopItem(GlowingSteelPickaxe(100), 200000, hashMapOf(), false) {
				GlowingSteelPickaxe(100).getUpdatedItem(false)
			},
			ShopItem(GlowingSteelShovel(100), 200000, hashMapOf(), false) {
				GlowingSteelShovel(100).getUpdatedItem(false)
			},
			ShopItem(GlowingSteelAxe(100), 200000, hashMapOf(), false) {
				GlowingSteelAxe(100).getUpdatedItem(false)
			},

			//TITANIUM
			ShopItem(TitaniumPickaxe(100), 300000, hashMapOf(), false) {
				TitaniumPickaxe(100).getUpdatedItem(false)
			},
			ShopItem(TitaniumShovel(100), 300000, hashMapOf(), false) {
				TitaniumShovel(100).getUpdatedItem(false)
			},
			ShopItem(TitaniumAxe(100), 300000, hashMapOf(), false) {
				TitaniumAxe(100).getUpdatedItem(false)
			},
			ShopItem(GRAYFILLER(-1), -1, hashMapOf(), false) {
				GRAYFILLER(-1).getUpdatedItem(false)
			},
			ShopItem(GlowingTitaniumPickaxe(100), 500000, hashMapOf(), false) {
				GlowingTitaniumPickaxe(100).getUpdatedItem(false)
			},
			ShopItem(GlowingTitaniumShovel(100), 500000, hashMapOf(), false) {
				GlowingTitaniumShovel(100).getUpdatedItem(false)
			},
			ShopItem(GlowingTitaniumAxe(100), 500000, hashMapOf(), false) {
				GlowingTitaniumAxe(100).getUpdatedItem(false)
			},
	                                            )
}