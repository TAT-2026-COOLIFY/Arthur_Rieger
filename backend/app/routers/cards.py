from typing import List, Optional
from fastapi import APIRouter, Depends, HTTPException, Query, status
from sqlalchemy.orm import Session

from ..database import get_db
from ..models import Card
from ..schemas import CardCreate, CardUpdate, CardResponse

router = APIRouter(prefix="/cards", tags=["Cards"])


@router.get("/", response_model=List[CardResponse])
def list_cards(
    search: Optional[str] = Query(None, description="Busca parcial pelo nome da carta"),
    game: Optional[str] = Query(None, description="Filtrar por franquia/jogo"),
    rarity: Optional[str] = Query(None, description="Filtrar por raridade"),
    db: Session = Depends(get_db),
):
    query = db.query(Card)

    if search:
        query = query.filter(Card.name.ilike(f"%{search}%"))
    if game and game != "Todos":
        query = query.filter(Card.game == game)
    if rarity and rarity != "Todas":
        query = query.filter(Card.rarity == rarity)

    return query.order_by(Card.id.desc()).all()


@router.get("/{card_id}", response_model=CardResponse)
def get_card(card_id: int, db: Session = Depends(get_db)):
    card = db.query(Card).filter(Card.id == card_id).first()
    if not card:
        raise HTTPException(status_code=404, detail="Carta não encontrada.")
    return card


@router.post("/", response_model=CardResponse, status_code=status.HTTP_201_CREATED)
def create_card(card_in: CardCreate, db: Session = Depends(get_db)):
    new_card = Card(**card_in.model_dump())
    db.add(new_card)
    db.commit()
    db.refresh(new_card)
    return new_card


@router.put("/{card_id}", response_model=CardResponse)
def update_card(card_id: int, card_in: CardUpdate, db: Session = Depends(get_db)):
    card = db.query(Card).filter(Card.id == card_id).first()
    if not card:
        raise HTTPException(status_code=404, detail="Carta não encontrada.")

    update_data = card_in.model_dump(exclude_unset=True)
    for field, value in update_data.items():
        setattr(card, field, value)

    db.commit()
    db.refresh(card)
    return card


@router.patch("/{card_id}/sell", response_model=CardResponse)
def sell_one_card(card_id: int, db: Session = Depends(get_db)):
    """Dá baixa de 1 unidade no estoque da carta."""
    card = db.query(Card).filter(Card.id == card_id).first()
    if not card:
        raise HTTPException(status_code=404, detail="Carta não encontrada.")
    if card.stock <= 0:
        raise HTTPException(status_code=400, detail="Carta sem estoque disponível para venda.")

    card.stock -= 1
    db.commit()
    db.refresh(card)
    return card


@router.delete("/{card_id}", status_code=status.HTTP_204_NO_CONTENT)
def delete_card(card_id: int, db: Session = Depends(get_db)):
    card = db.query(Card).filter(Card.id == card_id).first()
    if not card:
        raise HTTPException(status_code=404, detail="Carta não encontrada.")

    db.delete(card)
    db.commit()
    return None