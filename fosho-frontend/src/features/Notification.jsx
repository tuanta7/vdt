import { InboxStackIcon } from "@heroicons/react/24/outline";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client/dist/sockjs";

import { BASE_URL } from "../utils/constant";
import useGlobal from "../hooks/useGlobal";
import { useState, useRef, useEffect } from "react";
import toast from "react-hot-toast";

const Notification = () => {
  const [connected, setConnected] = useState(false);
  const stompClientRef = useRef(null);
  const {
    info: { user },
    stompClient: stompClientGlobal,
  } = useGlobal();

  const onMessageReceived = (payload) => {
    const msg = JSON.parse(payload.body).message;
    toast.success(msg);
  };

  useEffect(() => {
    const socket = new SockJS(`${BASE_URL}/ws`);
    const stompClient = new Client({
      webSocketFactory: () => socket,
      onConnect: () => {
        setConnected(true);
        stompClient.subscribe(
          `/user/${user?.email}/queue/notifications`,
          onMessageReceived
        );
        // Register the connected user
        stompClient.publish({
          destination: `/app/user.connect`,
          body: JSON.stringify("User Connected!"),
        });
      },
      onStompError: () => {
        console.error("Could not connect to WebSocket server.");
      },
    });

    stompClient.activate();
    stompClientRef.current = stompClient;
    stompClientGlobal.current = stompClient;

    return () => {
      if (stompClientRef.current) {
        stompClientRef.current.deactivate();
        stompClientGlobal.current = null;
      }
    };
  }, [user, stompClientGlobal]);

  const sendMessage = () => {
    if (stompClientRef.current && connected) {
      stompClientRef.current.publish({
        destination: `/app/notifications`,
        body: JSON.stringify({
          restaurant_id: 1,
          user_id: 2,
          from_user: false,
          message: "Thông báo tới nhà hàng!",
        }),
      });
    }
  };

  return (
    <button className="btn btn-ghost btn-circle btn-sm" onClick={sendMessage}>
      <InboxStackIcon className="w-5" />
    </button>
  );
};

export default Notification;
