export default function getCorrectObj(elementId, data) {
  const subjectTopic = data._embedded.materialModelList;
  const num = Number(elementId);
  let itemReturn;
  subjectTopic.forEach((topic) => {
    if (topic.id === num) {
      itemReturn = topic;
    }
  });

  return itemReturn;
}
