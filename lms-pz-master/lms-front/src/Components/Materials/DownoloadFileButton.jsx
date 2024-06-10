function DownoloadFileButton({ fileData, nameFile }) {
  const handleDownoload = () => {
    try {
      const decodedData = atob(fileData);
      const byteChar = decodedData.split("").map((char) => char.charCodeAt(0));
      const byteArray = new Uint8Array(byteChar);
      const blob = new Blob([byteArray], { type: "application/octet-stream" });

      const downoloadLink = document.createElement("a");
      downoloadLink.href = URL.createObjectURL(blob);
      downoloadLink.download = nameFile;
      downoloadLink.click();
    } catch (err) {
      console.error("Blad przy przetwarzaniu pliku", err);
    }
  };
  return <span onClick={handleDownoload}>{nameFile}</span>;
}

export default DownoloadFileButton;
