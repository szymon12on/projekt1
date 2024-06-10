/* eslint-disable react/prop-types */

import Loader from "../Helpers/Loader";
import CloseQuestion from "./CloseQuestion";
import OpenQuestion from "./OpenQuestion";
import TrueFalseQuestion from "./TrueFalseQuestion";
import FileQuestion from "./FileQuestion";
import { useState, useEffect, useContext } from "react";
import UserContext from "../Context/UserContext";
import swapItem from "./Hooks/swapItem";
import { useNavigate } from "react-router-dom";
function TaskContent({ contents, idTask }) {
  const [sentData, setSentData] = useState(false);
  const [finalData, setFinalData] = useState([]);
  const { userData } = useContext(UserContext);
  const navigate = useNavigate();
  function handleSubmit(e) {
    e.preventDefault();
    setSentData(true);
  }
  useEffect(
    function () {
      if (finalData.length !== 4) return;
      const token = JSON.parse(localStorage.getItem("token"));
      const headers = {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      };

      const sentDataToDatabase = async () => {
        const sortedArray = [...finalData].sort((a, b) =>
          a.typ.localeCompare(b.typ)
        );
        const sortedArrayCorrect = swapItem(sortedArray);

        const dataToSent = JSON.stringify(sortedArrayCorrect);

        const bodyData = {
          idZadania: idTask,
          idUcznia: userData.id,
          tresc: dataToSent,
        };

        const res = await fetch("/api/przedmiot/zadanie/odpowiedz", {
          method: "POST",
          headers: headers,
          body: JSON.stringify(bodyData),
        });

        // if (!res.ok) throw new Error("Blad w wyslanych danych");
        // const data = await res.json();
        // console.log(data);
        navigate("/materials");
      };

      sentDataToDatabase();
    },
    [finalData]
  );

  if (!contents) {
    return <Loader />;
  }

  return (
    <>
      <div>
        {contents.map((cont, index) => {
          if (cont.typ === "OTWARTE")
            return (
              <OpenQuestion
                key={index}
                element={cont}
                sentData={sentData}
                setFinalData={setFinalData}
                setSentData={setSentData}
              />
            );
          if (cont.typ === "ZAMKNIETE") {
            return (
              <CloseQuestion
                key={index}
                element={cont}
                sentData={sentData}
                setFinalData={setFinalData}
              />
            );
          }
          if (cont.typ === "PRAWDA_FALSZ") {
            return (
              <TrueFalseQuestion
                key={index}
                element={cont}
                sentData={sentData}
                setFinalData={setFinalData}
                setSentData={setSentData}
              />
            );
          }
          if (cont.typ === "PLIK") {
            return (
              <FileQuestion
                key={index}
                element={cont}
                sentData={sentData}
                setFinalData={setFinalData}
              />
            );
          }
        })}
      </div>
      <button
        className="translate-x-[650px] translate-y-[80px] bg-blue px-6 py-2 text-white rounded-lg"
        onClick={handleSubmit}
      >
        Wyślij zadania
      </button>
    </>
  );
}

export default TaskContent;
