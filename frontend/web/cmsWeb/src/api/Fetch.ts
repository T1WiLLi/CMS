export class Fetcher {

    private static instance: Fetcher;
    private API_URL_BASE: string = "https://cms-api-1crm.onrender.com";

    private constructor() {
        
    }

    public getInstance(): Fetcher {
        if (Fetcher.instance == null) {
            Fetcher.instance = new Fetcher();
        }
        return Fetcher.instance;
    }
}