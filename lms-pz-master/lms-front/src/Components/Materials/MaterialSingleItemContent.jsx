import getCorrectObj from "./Hooks/getCorrectObj";
import DownoloadFileButton from "./DownoloadFileButton";
import DownoloadFileButtonPdf from "./DownoloadFileButtonPdf";
import TaskMaterialContent from "./TaskMaterialContent";
function MaterialSingleItemContent({ setDisplayContentItem, data, actualRef }) {
  const elementId = actualRef.id;
  const correctObj = getCorrectObj(elementId, data);
  const fileData = correctObj.plik;
  const nameFile = correctObj.nazwaPliku;
  const pdfOrTxt = nameFile.includes(".txt");

  return (
    <div>
      <h1 className="text-2xl text-blue">Temat {correctObj.lp}</h1>
      <p className="mt-10 border-b border-gray border-opacity-20 pb-4 text-gray">
        <span className="text-xl text-green">Nazwa:</span> {correctObj.temat}
      </p>
      <p className="mt-5 border-b border-gray border-opacity-20 pb-4 text-gray">
        {" "}
        <span className="text-xl text-green">Opis: </span> {correctObj.opis}
      </p>
      <div className="mt-5 border-b border-gray border-opacity-20 pb-4 text-gray cursor-pointer flex items-center gap-x-2">
        {" "}
        <span className="text-xl text-green">Plik z zajęć : </span>
        {pdfOrTxt ? (
          <DownoloadFileButton fileData={fileData} nameFile={nameFile} />
        ) : (
          <DownoloadFileButtonPdf
            base64PdfData={fileData}
            nameFile={nameFile}
          />
        )}
      </div>
      <TaskMaterialContent task={correctObj.zadanie} />
      <p
        className="mt-10 translate-x-[-20px] text-gray opacity-50 cursor-pointer w-[30px]"
        onClick={() => {
          setDisplayContentItem(true);
        }}
      >
        Powrót
      </p>
    </div>
  );
}

export default MaterialSingleItemContent;
