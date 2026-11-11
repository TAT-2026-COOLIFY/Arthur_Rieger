from sqlalchemy import Column, Integer, String, Float
from .database import Base


class Card(Base):
    __tablename__ = "cards"

    id = Column(Integer, primary_key=True, index=True)
    name = Column(String(120), nullable=False, index=True)
    game = Column(String(50), nullable=False, index=True)
    set_code = Column(String(40), nullable=False)
    rarity = Column(String(60), nullable=False, index=True)
    price = Column(Float, nullable=False)
    stock = Column(Integer, nullable=False, default=0)