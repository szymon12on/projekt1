import { useState } from "react";
import { useOutletContext } from "react-router-dom";
import MaterialSingleDescription from "./MaterialSingleDescription";
import MaterialSingleItem from "./MaterialSingleItem";
import DescriptionSubject from "./DescriptionSubject";
import takeSubject from "./Hooks/takeSubject";
import useApi from "./Hooks/useApi";
import Loader from "../Helpers/Loader";
import MaterialSingleItemContent from "./MaterialSingleItemContent";
function SingleMaterial() {
  const [displayDescription, setDisplayDescription] = useState(false);
  const [nameMaterials, dataSubjects, isLoadingSubjets] = useOutletContext();
  const materialObj = btoa(takeSubject(nameMaterials, dataSubjects).kod);
  const [displayContentItem, setDisplayContentItem] = useState(true);

  const { data, isLoading, error } = useApi(
    `/api/przedmiot/material/all?kod=${materialObj}`
  );
  const [actualRef, setActualRef] = useState({});

  if (!data) {
    return <Loader />;
  }

  const subjectTopics = data._embedded.materialModelList;
  return (
    <div className="w-[90%] mx-auto mt-10">
      {displayContentItem ? (
        <>
          <MaterialSingleDescription
            setDisplayDescription={setDisplayDescription}
          />
          {displayDescription ? (
            <DescriptionSubject
              setDisplayDescription={setDisplayDescription}
              title={nameMaterials}
              data={dataSubjects}
              isLoading={isLoadingSubjets}
            />
          ) : (
            <>
              {subjectTopics.map((topic) => {
                return (
                  <MaterialSingleItem
                    key={topic.id}
                    topic={topic}
                    setDisplayContentItem={setDisplayContentItem}
                    id={topic.id}
                    setActualRef={setActualRef}
                  />
                );
              })}
            </>
          )}
        </>
      ) : (
        <MaterialSingleItemContent
          setDisplayContentItem={setDisplayContentItem}
          data={data}
          actualRef={actualRef}
        />
      )}
    </div>
  );
}
export default SingleMaterial;
