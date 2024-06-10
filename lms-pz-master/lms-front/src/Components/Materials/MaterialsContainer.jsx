function MaterialsContainer({ children }) {
  return (
    <div className="w-[90%] mx-auto mt-10 grid grid-cols-3 gap-y-10">
      {children}
    </div>
  );
}

export default MaterialsContainer;
