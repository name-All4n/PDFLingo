const pdfInput = document.getElementById("pdfInput");
const pdfContainer = document.getElementById("pdfContainer");
const translateBtn = document.getElementById("translateBtn");
const translatedText = document.getElementById("translatedText");

let selectedText = "";

pdfjsLib.GlobalWorkerOptions.workerSrc =
  "https://cdnjs.cloudflare.com/ajax/libs/pdf.js/3.11.174/pdf.worker.min.js";

pdfInput.addEventListener("change", async function () {
  const file = this.files[0];
  if (!file) return;

  const reader = new FileReader();

  reader.onload = async function () {
    const typedarray = new Uint8Array(this.result);
    const pdf = await pdfjsLib.getDocument(typedarray).promise;

    pdfContainer.innerHTML = "";

    for (let pageNum = 1; pageNum <= pdf.numPages; pageNum++) {
  const page = await pdf.getPage(pageNum);

  const scale = 1.6; // ⚠️ use valor inteiro
  const viewport = page.getViewport({ scale });

  const pageDiv = document.createElement("div");
  pageDiv.style.position = "relative";
  pageDiv.style.marginBottom = "20px";
  pageDiv.style.width = viewport.width + "px";
  pageDiv.style.height = viewport.height + "px";

  pdfContainer.appendChild(pageDiv);

  // CANVAS
  const canvas = document.createElement("canvas");
  const ctx = canvas.getContext("2d");

  canvas.width = viewport.width;
  canvas.height = viewport.height;
  canvas.style.width = viewport.width + "px";
  canvas.style.height = viewport.height + "px";

  pageDiv.appendChild(canvas);

  await page.render({
    canvasContext: ctx,
    viewport: viewport
  }).promise;

  // TEXT LAYER
  const textLayerDiv = document.createElement("div");
  textLayerDiv.className = "textLayer";
  textLayerDiv.style.width = viewport.width + "px";
  textLayerDiv.style.height = viewport.height + "px";

  pageDiv.appendChild(textLayerDiv);

  const textContent = await page.getTextContent();

  await pdfjsLib.renderTextLayer({
    textContent: textContent,
    container: textLayerDiv,
    viewport: viewport,
    textDivs: []
  });
}
  };

  reader.readAsArrayBuffer(file);
});

// Seleção
const tooltip = document.getElementById("tooltip");

document.addEventListener("mouseup", function () {
  const selection = window.getSelection();
  selectedText = selection.toString().trim();

  if (selectedText.length > 0) {
    const range = selection.getRangeAt(0);
    const rect = range.getBoundingClientRect();

    translateBtn.style.display = "block";
    translateBtn.style.top = `${rect.top + window.scrollY - 40}px`;
    translateBtn.style.left = `${rect.left + window.scrollX}px`;

  } else {
    translateBtn.style.display = "none";
    tooltip.style.display = "none";
  }
});

// Envio backend
translateBtn.addEventListener("click", async function () {
  if (!selectedText) return;

  try {
    const response = await fetch("http://localhost:8080/translate", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ phrase: selectedText })
    });

    const data = await response.json();

    const selection = window.getSelection();
    const range = selection.getRangeAt(0);
    const rect = range.getBoundingClientRect();

    tooltip.innerText = data.phrase;
    tooltip.style.display = "block";
    tooltip.style.top = `${rect.bottom + window.scrollY + 8}px`;
    tooltip.style.left = `${rect.left + window.scrollX}px`;

  } catch (error) {
    tooltip.innerText = "Erro ao traduzir.";
    tooltip.style.display = "block";
  }

  translateBtn.style.display = "none";
});