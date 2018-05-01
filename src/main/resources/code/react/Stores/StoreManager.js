import { RadarCollectionStore } from './RadarCollectionStore';

class StoreManager{
    constructor(){
        this.radarCollectionStore = {};
    }

    getRadarCollectionStore(){
        if(this.radarCollectionStore === undefined){
            this.radarCollectionStore = new RadarCollectionStore();
        }

        return this.radarCollectionStore;
    }
}

export default StoreManager;
