import { toast } from "react-toastify";

const mostraToastConferma = (messaggio: string, onConferma: () => void) => {
  toast.info(
    ({ closeToast }) => (
      <div className="toast-conferma">
        <p>{messaggio}</p>
        <div className="toast-conferma-buttons">
          <button
            onClick={() => {
              onConferma();
              closeToast();
            }}
            style={{ background: "green", color: "white", marginRight: "8px", padding: "5px 10px" }}
          >
            Sì
          </button>
          <button
            onClick={closeToast}
            style={{ background: "gray", color: "white", padding: "5px 10px" }}
          >
            No
          </button>
        </div>
      </div>
    ),
    {
      autoClose: false,
      closeOnClick: false,
      closeButton: false,
      draggable: false,
      position: "top-center"
    }
  );
};
