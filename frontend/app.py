import os
import requests
import streamlit as st

API_URL = os.getenv("API_URL", "http://localhost:8000")

st.set_page_config(
    page_title="TCG Card Shop Manager",
    page_icon="🃏",
    layout="wide",
)

GAMES_LIST = ["Yu-Gi-Oh!", "Pokémon TCG", "Magic: The Gathering", "One Piece Card Game", "Outro"]
RARITIES_LIST = [
    "Common",
    "Rare",
    "Super Rare",
    "Ultra Rare",
    "Secret Rare",
    "Quarter Century Secret Rare",
    "Special Illustration Rare",
    "ACE SPEC Rare",
    "Mythic Rare",
]


def fetch_cards(search="", game="Todos", rarity="Todas"):
    params = {}
    if search:
        params["search"] = search
    if game != "Todos":
        params["game"] = game
    if rarity != "Todas":
        params["rarity"] = rarity

    try:
        response = requests.get(f"{API_URL}/cards/", params=params, timeout=5)
        response.raise_for_status()
        return response.json()
    except requests.exceptions.RequestException as e:
        st.error(f"Erro ao conectar com a API ({API_URL}): {e}")
        return []


st.title("🃏 TCG Card Shop — Gestão de Estoque")
st.caption("Sistema CRUD integrado via API REST (FastAPI + Streamlit)")

# Busca geral para calcular as métricas do topo
all_cards = fetch_cards()

total_titles = len(all_cards)
total_units = sum(c["stock"] for c in all_cards)
total_value = sum(c["price"] * c["stock"] for c in all_cards)

col_m1, col_m2, col_m3 = st.columns(3)
col_m1.metric("Cartas Cadastradas (Títulos)", total_titles)
col_m2.metric("Total de Unidades em Estoque", f"{total_units} un.")
col_m3.metric("Valor Total do Inventário", f"R$ {total_value:,.2f}")

st.divider()

tab_catalogo, tab_cadastro, tab_edicao = st.tabs([
    "📦 Catálogo & Venda Rápida",
    "➕ Cadastrar Nova Carta",
    "✏️ Editar ou Excluir Carta",
])

# ================= ABA 1: CATÁLOGO E VENDA RÁPIDA =================
with tab_catalogo:
    col_f1, col_f2, col_f3 = st.columns([2, 1, 1])
    with col_f1:
        search_term = st.text_input("🔍 Buscar por nome da carta", placeholder="Ex: Infernity, Iron Crown...")
    with col_f2:
        game_filter = st.selectbox("🎮 Jogo / Franquia", ["Todos"] + GAMES_LIST)
    with col_f3:
        rarity_filter = st.selectbox("✨ Raridade", ["Todas"] + RARITIES_LIST)

    filtered_cards = fetch_cards(search=search_term, game=game_filter, rarity=rarity_filter)

    if not filtered_cards:
        st.info("Nenhuma carta encontrada com esses filtros.")
    else:
        for card in filtered_cards:
            with st.container(border=True):
                c1, c2, c3, c4, c5 = st.columns([3, 2, 1.5, 1.5, 1.5])
                with c1:
                    st.markdown(f"**{card['name']}**")
                    st.caption(f"ID #{card['id']} • Set: `{card['set_code']}`")
                with c2:
                    st.write(f"🎮 {card['game']}")
                    st.caption(f"✨ {card['rarity']}")
                with c3:
                    st.markdown(f"**R$ {card['price']:,.2f}**")
                with c4:
                    if card["stock"] > 0:
                        st.success(f"Estoque: {card['stock']} un.")
                    else:
                        st.error("Esgotado (0 un.)")
                with c5:
                    if st.button(
                        "🛒 Vender 1 un.",
                        key=f"sell_{card['id']}",
                        disabled=card["stock"] <= 0,
                        use_container_width=True,
                    ):
                        res = requests.patch(f"{API_URL}/cards/{card['id']}/sell", timeout=5)
                        if res.status_code == 200:
                            st.toast(f"Venda registrada: {card['name']}!", icon="✅")
                            st.rerun()
                        else:
                            st.error(res.json().get("detail", "Erro ao vender."))

# ================= ABA 2: CADASTRAR NOVA CARTA =================
with tab_cadastro:
    st.subheader("Cadastrar Carta no Estoque")
    with st.form("create_card_form", clear_on_submit=True):
        col1, col2 = st.columns(2)
        with col1:
            name = st.text_input("Nome da Carta *", placeholder="Ex: Blue-Eyes White Dragon")
            game = st.selectbox("Jogo / Franquia *", GAMES_LIST)
            set_code = st.text_input("Código da Coleção / Set *", placeholder="Ex: LOB-001")
        with col2:
            rarity = st.selectbox("Raridade *", RARITIES_LIST)
            price = st.number_input("Preço Unitário (R$) *", min_value=0.50, value=19.90, step=1.00)
            stock = st.number_input("Quantidade Inicial em Estoque *", min_value=0, value=1, step=1)

        submitted = st.form_submit_button("💾 Cadastrar Carta", use_container_width=True)
        if submitted:
            if not name.strip() or not set_code.strip():
                st.warning("Preencha o nome da carta e o código do Set.")
            else:
                payload = {
                    "name": name.strip(),
                    "game": game,
                    "set_code": set_code.strip(),
                    "rarity": rarity,
                    "price": price,
                    "stock": stock,
                }
                res = requests.post(f"{API_URL}/cards/", json=payload, timeout=5)
                if res.status_code == 201:
                    st.success(f"Carta '{name}' cadastrada com sucesso!")
                    st.rerun()
                else:
                    st.error(f"Erro ao cadastrar: {res.text}")

# ================= ABA 3: EDITAR OU EXCLUIR =================
with tab_edicao:
    st.subheader("Atualizar ou Remover Carta")
    if not all_cards:
        st.info("Não há cartas cadastradas para editar.")
    else:
        options = {f"#{c['id']} - {c['name']} ({c['set_code']})": c for c in all_cards}
        selected_label = st.selectbox("Selecione a carta", list(options.keys()))
        selected_card = options[selected_label]

        with st.form("edit_card_form"):
            col1, col2 = st.columns(2)
            with col1:
                edit_name = st.text_input("Nome da Carta", value=selected_card["name"])
                game_idx = GAMES_LIST.index(selected_card["game"]) if selected_card["game"] in GAMES_LIST else 0
                edit_game = st.selectbox("Jogo / Franquia", GAMES_LIST, index=game_idx)
                edit_set = st.text_input("Código do Set", value=selected_card["set_code"])
            with col2:
                rarity_idx = RARITIES_LIST.index(selected_card["rarity"]) if selected_card["rarity"] in RARITIES_LIST else 0
                edit_rarity = st.selectbox("Raridade", RARITIES_LIST, index=rarity_idx)
                edit_price = st.number_input("Preço (R$)", min_value=0.50, value=float(selected_card["price"]), step=1.00)
                edit_stock = st.number_input("Estoque", min_value=0, value=int(selected_card["stock"]), step=1)

            col_btn1, col_btn2 = st.columns(2)
            with col_btn1:
                save_btn = st.form_submit_button("💾 Salvar Alterações", use_container_width=True)
            with col_btn2:
                delete_btn = st.form_submit_button("🗑️ Excluir Carta", type="primary", use_container_width=True)

            if save_btn:
                payload = {
                    "name": edit_name.strip(),
                    "game": edit_game,
                    "set_code": edit_set.strip(),
                    "rarity": edit_rarity,
                    "price": edit_price,
                    "stock": edit_stock,
                }
                res = requests.put(f"{API_URL}/cards/{selected_card['id']}", json=payload, timeout=5)
                if res.status_code == 200:
                    st.success("Carta atualizada com sucesso!")
                    st.rerun()
                else:
                    st.error(f"Erro ao atualizar: {res.text}")

            if delete_btn:
                res = requests.delete(f"{API_URL}/cards/{selected_card['id']}", timeout=5)
                if res.status_code == 204:
                    st.success("Carta removida do catálogo!")
                    st.rerun()
                else:
                    st.error(f"Erro ao excluir: {res.text}")