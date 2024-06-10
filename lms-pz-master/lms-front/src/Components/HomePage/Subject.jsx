function Subject({ name, index }) {
  return (
    <div className="flex ml-5 items-center gap-x-10 mt-5 pt-5 pb-3 border-b-[1px] border-opacity-5 border-gray">
      <p className="text-gray">{index}</p>
      <div>
        <h6>{name}</h6>
        <p className="opacity-50 text-[13px]">
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Rerum,
          voluptatibus?
        </p>
      </div>
    </div>
  );
}

export default Subject;
