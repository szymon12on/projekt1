/* eslint-disable react/prop-types */
import { useState, useEffect } from "react";
function TrueFalseQuestion({ element, sentData, setFinalData }) {
  const [selectedOption, setSelectedOption] = useState(null);

  function handleInput(e) {
    setSelectedOption(e.target.value);
  }

  useEffect(() => {
    const sentDataToParent = () => {
      if (!sentData) return;
      let obj = {
        typ: "PRAWDA_FALSZ",
        odpowiedz: selectedOption,
        punkty: 0.0,
      };
      setFinalData((data) => [...data, obj]);
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
          <div className="flex gap-x-10">
            <label className="flex gap-x-3 ">
              True
              <input
                name="myRadio"
                type="radio"
                value={"true"}
                onChange={(e) => handleInput(e)}
              />
            </label>
            <label className="flex gap-x-3 ">
              False
              <input
                name="myRadio"
                type="radio"
                onChange={(e) => handleInput(e)}
                value={"false"}
              />
            </label>
          </div>
          <div className="flex justify-end">
            <p>0/{element.punkty}</p>
          </div>
        </form>
      </div>
    </div>
  );
}

export default TrueFalseQuestion;
