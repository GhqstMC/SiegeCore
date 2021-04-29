package net.siegerpg.siege.core.items.types.subtypes

import net.kyori.adventure.text.format.TextDecoration
import net.siegerpg.siege.core.items.*
import net.siegerpg.siege.core.items.enums.Rarity
import net.siegerpg.siege.core.items.enums.StatTypes
import net.siegerpg.siege.core.utils.Utils
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

interface CustomEquipment : CustomItem {

    var statGem: StatGem?
    val baseStats: HashMap<StatTypes, Double>

    fun addStatGem(newStatGem: StatGem) {
        this.statGem = newStatGem
        println("serializing")
    }

    override fun updateMeta(hideRarity: Boolean): ItemStack {
        val meta = item.itemMeta

        val shownRarity = if (hideRarity) Rarity.UNCOMMON else rarity

        meta.displayName(Utils.lore(if (shownRarity == Rarity.SPECIAL) "<r><rainbow>$name</rainbow>" else "<r>${shownRarity.color}$name"))

        val newLore =
            mutableListOf(Utils.lore(if (shownRarity == Rarity.SPECIAL) "<r><rainbow>${shownRarity.id}</rainbow> <gray>${if (hideRarity) 50 else quality}%" else "<r>${shownRarity.color}${shownRarity.id} <gray>${if (hideRarity) 50 else quality}%"))
        statGem?.let {
            newLore.add(Utils.lore(" "))
            newLore.add(Utils.lore("<r><color:#FF3CFF>+${it.amount} <light_purple>${it.type.stylizedName} Gem"))
        }
        if (baseStats.size != 0) {
            newLore.add(Utils.lore(" "))
            val realStats = CustomItemUtils.getStats(this, addGem = false, addRarity = true)
            baseStats.keys.forEach {
                newLore.add(Utils.lore("<r><green>+${realStats[it]} <gray>${it.stylizedName}"))
            }
        }
        newLore.add(Utils.lore(" "))
        description.forEach {
            newLore.add(Utils.lore("<r><dark_gray>$it"))
        }
        newLore.add(Utils.lore(" "))
        newLore.add(Utils.lore("<r><gray>Level: $levelRequirement"))
        if (hideRarity) newLore.add(Utils.lore("<r><red>This is not the real item"))
        meta.lore(newLore)

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
        item.itemMeta = meta
        return item
    }

    override fun serialize() {
        super.serialize()
        item = item.setNbtTags(
            "equipmentStatGem" to if (statGem != null) statGem.toString() else null
        )
    }

    override fun deserialize() {
        super.deserialize()
        item.getNbtTag<String>("equipmentStatGem")?.let {
            statGem = StatGem.fromString(it)
        }
    }
}