import Loader from "../Helpers/Loader";

function DescriptionSubject({ setDisplayDescription, title, data, isLoading }) {
  let actualSubject = null;

  const subjects = data._embedded.przedmiotModelList;
  subjects.forEach((subject) => {
    if (subject.nazwa === title) {
      actualSubject = subject;
    }
  });

  return (
    <div className="mt-10">
      {isLoading ? (
        <Loader />
      ) : (
        <>
          <div className="border-b-[1px] border-gray border-opacity-15 pb-5">
            <h3 className="text-xl">Nazwa przedmiotu</h3>
            <p>{actualSubject.nazwa}</p>
          </div>
          <div className="border-b-[1px] border-gray border-opacity-15 pb-5 pt-5">
            <h4 className="text-xl">Opis Przedmiotu</h4>
            <p>{actualSubject.opis}</p>
          </div>
          <div className="border-b-[1px] border-gray border-opacity-15 pb-5 pt-5">
            <h5 className="text-xl">Warunki Zaliczenia</h5>
            <p>{actualSubject.warunkiZaliczenia}</p>
          </div>
          <p
            className="mt-10 cursor-pointer"
            onClick={() => setDisplayDescription(false)}
          >
            Powrót
          </p>
        </>
      )}
    </div>
  );
}

export default DescriptionSubject;
