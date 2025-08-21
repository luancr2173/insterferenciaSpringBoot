// Google Maps JS API
// - initMap é chamado via callback no script do Google Maps em index.html
// - setMapMarker é usado pelo app para centralizar/atualizar o marcador

let gmap;     // mapa
let gmarker;  // marcador atual

function initMap() {
  // Centro padrão (Brasília)
  const center = { lat: -15.8, lng: -47.9 };

  gmap = new google.maps.Map(document.getElementById("map"), {
    center,
    zoom: 10,
    mapTypeControl: false,
    streetViewControl: false,
    fullscreenControl: true
  });

  // Clique no mapa → preencher inputs e marcar
  gmap.addListener("click", (e) => {
    const lat = e.latLng.lat();
    const lng = e.latLng.lng();

    const latInput = document.getElementById("latitude");
    const lngInput = document.getElementById("longitude");
    if (latInput && lngInput) {
      latInput.value = lat.toFixed(6);
      lngInput.value = lng.toFixed(6);
    }

    setMapMarker(lat, lng);
  });
}

function setMapMarker(lat, lng) {
  if (!gmap) return;
  const pos = { lat: Number(lat), lng: Number(lng) };
  if (gmarker) gmarker.setMap(null);
  gmarker = new google.maps.Marker({ position: pos, map: gmap });
  gmap.setCenter(pos);
  gmap.setZoom(14);
}

// Expor globais
window.initMap = initMap;
window.setMapMarker = setMapMarker;
