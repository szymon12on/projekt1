/* eslint-disable react/prop-types */
import { useEffect, useRef } from "react";

const fileToBase64 = (file, callback) => {
  const reader = new FileReader();
  reader.onloadend = () => {
    callback(reader.result);
  };
  reader.readAsDataURL(file);
};

function FileQuestion({ element, sentData, setFinalData }) {
  const fileRef = useRef(null);

  useEffect(() => {
    const sentDataToParent = () => {
      if (!sentData) return;

      const fileInput = fileRef.current;
      if (!fileInput || !fileInput.files || fileInput.files.length === 0) {
        // Użytkownik nie wybrał pliku, obsłuż to odpowiednio
        let obj = {
          typ: element.typ,
          odpowiedz: "", // Pusty string dla braku pliku
          punkty: 0.0,
        };
        setFinalData((data) => [...data, obj]);
        return;
      }

      const file = fileInput.files[0];
      fileToBase64(file, (base64Data) => {
        const fileBase = base64Data.split(",")[1];

        let obj = {
          typ: element.typ,
          odpowiedz: fileBase,
          punkty: 0.0,
        };
        setFinalData((data) => [...data, obj]);
      });
    };

    sentDataToParent();
  }, [sentData]);

  return (
    <div className="mt-10 border-t-[1px] pt-3 border-gray border-opacity-20">
      <h2 className="text-lg text-green py-5">Pytanie: </h2>
      <p className="mt-3 text-gray opacity-70">{element.pytanie}</p>
      <div className="mt-3">
        <h3 className="text-blue">Odpowiedzi</h3>
        <form className="flex flex-col mt-5">
          <label className="flex flex-col gap-y-2">
            Wybierz plik:
            <input type="file" ref={fileRef} />
          </label>
          <div className="flex justify-end">
            <p>0/{element.punkty}</p>
          </div>
        </form>
      </div>
    </div>
  );
}

export default FileQuestion;
