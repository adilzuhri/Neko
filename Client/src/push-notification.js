import firebase from 'firebase';

export const intializeFirebase = () => {
    firebase.intializeApp({
        messagingSenderId: "your messagingSenderId"
    });
}

export const askForPermissioToReceiveNotifications = async () => {
    try {
        const messaging = firebase.messaging();
        await messaging.requestPermission();
        const token = await messaging.getToken();
        console.log('user token: ', token);

        return token;
    } catch (error) {
        console.error(error);
    }
}