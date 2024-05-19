import {create} from 'zustand';

const useStore = create((set) => ({
  location: { lat: null, lng: null },
  setLocation: (lat, lng) => set({ location: { lat, lng } }),
}));

export default useStore;
