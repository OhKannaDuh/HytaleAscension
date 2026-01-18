# Stats Module

The Stats Module manages **experience (EXP)** and various **Ascension attributes** for players.

---

## Experience (EXP)

EXP is earned when dealing damage to mobs.
- EXP is **shared with party members**, ensuring balanced progression across all jobs rather than giving all XP to the damage dealer.
- Handled via the `AddExpEvent` system.

---

## Attributes

### Vitality

Vitality increases the player’s **survivability**:

- **Max Health:** +10 per point
- **Max Oxygen:** +5 per point

**Modifiers:**

- `VitalityHealthAttributeModifier` → adds health
- `VitalityOxygenAttributeModifier` → adds oxygen

**Health Regeneration:**

Depending on invested points, health regeneration is applied in tiers:

| Tier | Level Requirement | Effect                  | JSON                                     |
|------|-------------------|-------------------------|------------------------------------------|
| T1   | 5                 | 1% HP every 5 seconds   | `Ascension.Vitality.HealthRegen.T1.json` |
| T2   | 15                | 2.5% HP every 5 seconds | `Ascension.Vitality.HealthRegen.T2.json` |
| T3   | 25                | 5% HP every 5 seconds   | `Ascension.Vitality.HealthRegen.T3.json` |

- Controlled via `VitalityHealthRegenEffectApplicator`, which determines which effect tier is applied based on the player’s Vitality level.

### Defense

Defense reduces incoming damage based on the player’s defense level.

- **Effect:** Each point of Defense grants **2.5% damage reduction**.
- **Calculation:** `finalDamage = incomingDamage * (1 - 0.025 * defenseLevel)`
- **Implementation:** Handled by the `ApplyDefenseSystem`.

> Example: A player with 4 Defense takes 10% less damage from attacks.