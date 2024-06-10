function OptionContainer({ data, setPeriod }) {
  return (
    <select
      defaultValue="okres1"
      className="ml-10 mt-5 p-2 rounded-lg bg-transparent border border-blue px-3"
      onChange={(e) => setPeriod(e.target.value)}
    >
      {data.map((element, index) => {
        return (
          <option key={index} value={element.okres}>
            {element.okres}
          </option>
        );
      })}
    </select>
  );
}

export default OptionContainer;
