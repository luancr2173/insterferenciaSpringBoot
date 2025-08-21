// ====== Finalidades dinâmicas ======
function adicionarFinalidade(nome = "", vazaoMensal = "") {
  const container = document.getElementById("finalidadesContainer");
  const div = document.createElement("div");
  div.classList.add("finalidade-item");
  div.innerHTML = `
    <input type="text" placeholder="Descrição" value="${nome}" required style="width:40%;">
    <input type="number" step="any" placeholder="Vazão mensal (m³)" value="${vazaoMensal}" required style="width:30%;">
    <button type="button" id="btnRemoverFinalidade" title="Remover finalidade" aria-label="Remover finalidade">X</button>
  `;
  div.querySelector("button").addEventListener("click", () => div.remove());
  container.appendChild(div);
}
window.adicionarFinalidade = adicionarFinalidade;

// Botão adicionar finalidade
document.getElementById("btnAddFinalidade").addEventListener("click", () => adicionarFinalidade());

// ====== Estado e utilidades ======
let interferenciasCache = [];

function toast(msg) {
  // Simples alerta substituível por um snackbar/toast real
  console.log(msg);
}

// ====== Map ======
let map;
let marker;

function initMap() {
  map = new google.maps.Map(document.getElementById("map"), {
    center: { lat: -15.7942, lng: -47.8822 }, // Brasília como centro inicial
    zoom: 12,
  });
}
window.initMap = initMap;

function setMapMarker(lat, lng) {
  if (!map) return;
  const position = { lat, lng };
  if (marker) {
    marker.setPosition(position);
  } else {
    marker = new google.maps.Marker({
      position,
      map,
    });
  }
  map.panTo(position);
}
window.setMapMarker = setMapMarker;

// ====== CRUD ======
async function carregarInterferencias() {
  try {
    const res = await fetch("/interferencias");
    if (!res.ok) throw new Error("Erro ao carregar interferências");
    const dados = await res.json();
    interferenciasCache = dados;
    renderizarTabela(dados);
  } catch (e) {
    alert(e.message);
  }
}
window.carregarInterferencias = carregarInterferencias;

function renderizarTabela(lista) {
  const tbody = document.querySelector("#tabelaInterferencias tbody");
  tbody.innerHTML = "";

  lista.forEach((item) => {
    const totalVazao = (item.finalidades || []).reduce((s, f) => s + (Number(f.vazaoMensal) || 0), 0);
    const finalidadesNomes = (item.finalidades || []).map(f => f.nome).join(", ") || "(nenhuma)";

    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${item.id}</td>
      <td>${item.descricao}</td>
      <td>${item.latitude}</td>
      <td>${item.longitude}</td>
      <td>${finalidadesNomes}</td>
      <td>${totalVazao}</td>
      <td>
        <button class="btn-editar">Editar</button>
        <button class="btn-excluir" title="Excluir">🗑️</button>
      </td>
    `;

    tr.querySelector(".btn-editar").addEventListener("click", () => editarInterferencia(item.id));
    tr.querySelector(".btn-excluir").addEventListener("click", () => excluirInterferencia(item.id));

    tbody.appendChild(tr);
  });
}

// ====== Busca ======
document.getElementById("campoBusca").addEventListener("input", (e) => {
  const termo = e.target.value.toLowerCase();
  const filtrados = interferenciasCache.filter(
    (item) =>
      item.descricao.toLowerCase().includes(termo) ||
      (item.finalidades && item.finalidades.some((f) => (f.nome || "").toLowerCase().includes(termo)))
  );
  renderizarTabela(filtrados);
});

document.getElementById("btnLimparBusca").addEventListener("click", () => {
  document.getElementById("campoBusca").value = "";
  renderizarTabela(interferenciasCache);
});

// ====== Formulário ======
document.getElementById("formInterferencia").addEventListener("submit", async (e) => {
  e.preventDefault();
  const id = document.getElementById("id").value;
  const descricao = document.getElementById("descricao").value;
  const latitude = parseFloat(document.getElementById("latitude").value);
  const longitude = parseFloat(document.getElementById("longitude").value);

  const finalidades = Array.from(document.querySelectorAll("#finalidadesContainer .finalidade-item")).map((div) => {
    const inputs = div.querySelectorAll("input");
    return { nome: inputs[0].value, vazaoMensal: parseFloat(inputs[1].value) };
  });

  const metodo = id ? "PUT" : "POST";
  const url = id ? `/interferencias/${id}` : "/interferencias";

  const res = await fetch(url, {
    method: metodo,
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ descricao, latitude, longitude, finalidades }),
  });

  if (!res.ok) {
    alert("Erro ao salvar interferência");
    return;
  }

  cancelarEdicao();
  await carregarInterferencias();

  // Centraliza o mapa no ponto salvo
  setMapMarker(latitude, longitude);
});

// ====== Editar/Excluir ======
async function editarInterferencia(id) {
  const res = await fetch(`/interferencias/${id}`);
  if (!res.ok) return alert("Erro ao carregar interferência");
  const dados = await res.json();

  document.getElementById("formInterferencia").querySelector("h2").textContent = "Editar Interferência";
  document.getElementById("id").value = dados.id;
  document.getElementById("descricao").value = dados.descricao;
  document.getElementById("latitude").value = dados.latitude;
  document.getElementById("longitude").value = dados.longitude;

  const container = document.getElementById("finalidadesContainer");
  container.innerHTML = "";
  (dados.finalidades || []).forEach((f) => adicionarFinalidade(f.nome, f.vazaoMensal));

  setMapMarker(dados.latitude, dados.longitude);
}
window.editarInterferencia = editarInterferencia;

async function excluirInterferencia(id) {
  if (!confirm("Tem certeza que deseja excluir esta interferência?")) return;
  const res = await fetch(`/interferencias/${id}`, { method: "DELETE" });
  if (!res.ok) {
    alert("Erro ao excluir interferência");
    return;
  }
  await carregarInterferencias();
}
window.excluirInterferencia = excluirInterferencia;

function cancelarEdicao() {
  document.getElementById("id").value = "";
  document.getElementById("formInterferencia").reset();
  document.getElementById("finalidadesContainer").innerHTML = "";
  document.getElementById("formInterferencia").querySelector("h2").textContent = "Nova Interferência";
}
window.cancelarEdicao = cancelarEdicao;

// ====== Modo claro/escuro ======
document.getElementById("toggleMode").addEventListener("change", () => {
  document.body.classList.toggle("light");
});

// ====== Inicializa ======
window.addEventListener("load", () => {
  if (typeof google !== "undefined") initMap();
  carregarInterferencias();
});
