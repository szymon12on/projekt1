import NotificationItem from "./NotificationItem";

function ModalNotificationComponent({ notificationData, setOpenModal }) {
  return (
    <div className="absolute right-[120px] top-[120px] bg-white scrollbar-thin scrollbar-thumb-green scrollbar-track-white max-h-[200px] rounded-lg w-[500px] z-50 overflow-y-scroll border border-green pb-5">
      <div className="w-[100%] justify-end">
        <p
          className="text-right pr-2 pt-3 cursor-pointer"
          onClick={() => setOpenModal((flag) => !flag)}
        >
          &#10006;
        </p>
      </div>
      <div className="pl-3 flex flex-col gap-y-3">
        {notificationData.map((el, index) => {
          if (el.flaga === "NOWA") {
            return (
              <NotificationItem key={index} content={el.tresc} id={el.id} />
            );
          }
        })}
      </div>
    </div>
  );
}

export default ModalNotificationComponent;
