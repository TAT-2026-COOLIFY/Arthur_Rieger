from typing import Optional
from pydantic import BaseModel, Field, ConfigDict


class CardBase(BaseModel):
    name: str = Field(..., min_length=2, max_length=120, examples=["Infernity Archfiend"])
    game: str = Field(..., examples=["Yu-Gi-Oh!"])
    set_code: str = Field(..., min_length=2, max_length=40, examples=["TSHD-EN018"])
    rarity: str = Field(..., examples=["Ultra Rare"])
    price: float = Field(..., gt=0, description="Preço unitário em R$", examples=[45.90])
    stock: int = Field(..., ge=0, description="Quantidade disponível", examples=[4])


class CardCreate(CardBase):
    pass


class CardUpdate(BaseModel):
    name: Optional[str] = Field(None, min_length=2, max_length=120)
    game: Optional[str] = None
    set_code: Optional[str] = None
    rarity: Optional[str] = None
    price: Optional[float] = Field(None, gt=0)
    stock: Optional[int] = Field(None, ge=0)


class CardResponse(CardBase):
    id: int
    model_config = ConfigDict(from_attributes=True)