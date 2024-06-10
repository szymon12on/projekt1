import { useRef } from "react";

function MaterialSingleItem({
  num,
  topic,
  setDisplayContentItem,
  id,
  setActualRef,
}) {
  const refItem = useRef(null);
  function handleSetRef() {
    const element = refItem.current;
    setActualRef(element);
  }

  return (
    <div className="mt-10 border-b-[1px] border-gray border-opacity-15">
      <h5
        id={id}
        className="text-xl py-3 cursor-pointer text-blue"
        onClick={() => {
          setDisplayContentItem(false);
          handleSetRef();
        }}
        ref={refItem}
      >
        Temat {topic.lp}
      </h5>
    </div>
  );
}

export default MaterialSingleItem;
