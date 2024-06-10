const DownloadFileButtonPdf = ({ base64PdfData, nameFile }) => {
  const handleDownload = () => {
    const decodedData = atob(base64PdfData);

    const byteCharacters = decodedData
      .split("")
      .map((char) => char.charCodeAt(0));
    const byteArray = new Uint8Array(byteCharacters);

    const blob = new Blob([byteArray], { type: "application/pdf" });

    const downloadLink = document.createElement("a");
    downloadLink.href = URL.createObjectURL(blob);
    downloadLink.download = nameFile || "document.pdf";
    downloadLink.click();
  };

  return <button onClick={handleDownload}>{nameFile}</button>;
};

export default DownloadFileButtonPdf;
