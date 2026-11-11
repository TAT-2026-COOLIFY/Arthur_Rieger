from sqlalchemy.orm import Session
from .models import Card


def seed_database(db: Session):
    """Insere cartas iniciais caso o banco de dados esteja vazio."""
    if db.query(Card).count() > 0:
        return

    initial_cards = [
        Card(
            name="Infernity Archfiend",
            game="Yu-Gi-Oh!",
            set_code="OTR-EN001",
            rarity="Ultra Rare",
            price=64.90,
            stock=3,
        ),
        Card(
            name="Worldsea Dragon Zealantis",
            game="Yu-Gi-Oh!",
            set_code="RA02-EN045",
            rarity="Quarter Century Secret Rare",
            price=189.00,
            stock=2,
        ),
        Card(
            name="Ash Blossom & Joyous Spring",
            game="Yu-Gi-Oh!",
            set_code="RA01-EN008",
            rarity="Secret Rare",
            price=35.50,
            stock=8,
        ),
        Card(
            name="Iron Crown ex",
            game="Pokémon TCG",
            set_code="TEF-191/162",
            rarity="Special Illustration Rare",
            price=240.00,
            stock=1,
        ),
        Card(
            name="Prime Catcher (ACE SPEC)",
            game="Pokémon TCG",
            set_code="TEF-157/162",
            rarity="ACE SPEC Rare",
            price=95.00,
            stock=4,
        ),
        Card(
            name="The One Ring",
            game="Magic: The Gathering",
            set_code="LTR-246",
            rarity="Mythic Rare",
            price=420.00,
            stock=2,
        ),
    ]

    db.add_all(initial_cards)
    db.commit()