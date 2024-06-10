function NotificationItem({ content, id }) {
  const handleRemoveItem = async function () {
    const token = JSON.parse(localStorage.getItem("token"));
    const headers = {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    };

    const res = await fetch(`/api/powiadomienie/${id}?flaga=przeczytana`, {
      method: "PATCH",
      headers: headers,
    });
  };

  return (
    <>
      <div className="flex flex-col gap-y-1 border-b-[1px] border-green">
        <p className=" pb-2 text-gray opacity-70">{content}</p>
        <button className="text-red pb-2" onClick={handleRemoveItem}>
          Usuń
        </button>
      </div>
    </>
  );
}

export default NotificationItem;
