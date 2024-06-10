export default function takeSubject(name, arr) {
  let helper;
  const arrNew = arr._embedded.przedmiotModelList;
  arrNew.forEach((el) => {
    if (el.nazwa === name) {
      helper = el;
    }
  });
  //   let helper = null;
  //   arr.forEach((el) => {
  //     if (el.nazwa === name) {
  //       helper = el;
  //     }
  //   });

  return helper;
}
