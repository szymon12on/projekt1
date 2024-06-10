/* eslint-disable react/prop-types */
import LabelFormComponent from "./LabelFormComponent";
import { createRef, useEffect } from "react";

function CloseQuestion({ element, sentData, setFinalData }) {
  const refAnswer = [createRef(), createRef(), createRef(), createRef()];

  useEffect(
    function () {
      const sentToParentComponent = function () {
        if (!sentData || !refAnswer) return;
        const textTab = [];
        const indexTab = [];
        refAnswer.forEach((answer, index) => {
          const inpt = answer.current.children[1];
          if (inpt.checked) {
            const textCont = answer.current.textContent.split(" ")[1];
            indexTab.push(index + 1);
            textTab.push(textCont);
          }
        });
        let obj = {
          typ: element.typ,
          odpowiedz: indexTab,
          punkty: 0.0,
        };
        setFinalData((data) => [...data, obj]);
      };

      sentToParentComponent();
    },
    [sentData]
  );

  return (
    <div className="mt-10 border-t-[1px] pt-3 border-gray border-opacity-20">
      <h2 className="text-lg text-green py-5">Pytanie: </h2>
      <p className="mt-3 text-gray opacity-70">{element.pytanie}</p>
      <div className="mt-3">
        <h3 className="text-blue">Odpowiedzi</h3>
        <form className="grid grid-cols-2 mt-10 space-y-2">
          {element.odpowiedz.map((answer, index) => (
            <LabelFormComponent
              key={index}
              id={index}
              content={answer}
              refAnswer={refAnswer[index]}
            />
          ))}
        </form>
        <div className="flex justify-end items-center mt-2 pb-3">
          <p>0/{element.punkty}</p>
        </div>
      </div>
    </div>
  );
}

export default CloseQuestion;
