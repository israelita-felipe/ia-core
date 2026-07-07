import html2canvas from "html2canvas";

window.captureScreenshot = async function (element) {

    if (!element) {
        console.error("Elemento não informado.");
        return null;
    }

    // Caso venha um wrapper do Vaadin
    if (element.$server) {
        element = element.$server;
    }

    // Garante que seja um HTMLElement
    if (!(element instanceof HTMLElement)) {
        console.error("Objeto recebido não é um HTMLElement.", element);
        return null;
    }

    try {

        await new Promise(resolve => requestAnimationFrame(resolve));

        const canvas = await html2canvas(element, {
            scale: window.devicePixelRatio || 2,
            useCORS: true,
            allowTaint: false,
            logging: true,
            backgroundColor: "#ffffff"
        });

        return canvas.toDataURL("image/png").split(",")[1];

    } catch (e) {

        console.error("Erro ao gerar screenshot", e);

        return null;
    }
};
