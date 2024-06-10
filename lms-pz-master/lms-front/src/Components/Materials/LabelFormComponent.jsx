function LabelFormComponent({ content, id, refAnswer }) {
  return (
    <label className="flex gap-x-2 items-center" ref={refAnswer}>
      <p>
        {id + 1}) {content}
      </p>
      <input type="checkbox" id={id} className="rounded-full" />
    </label>
  );
}

export default LabelFormComponent;
