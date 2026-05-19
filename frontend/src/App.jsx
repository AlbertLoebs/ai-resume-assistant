import { useEffect, useState } from 'react'

function App() {
  const [status, setStatus] = useState('loading...')

  useEffect(() => {
    fetch('/api/v1/health')
      .then((res) => res.json())
      .then((data) => setStatus(data.status))
      .catch((err) => setStatus('error: ' + err.message))
  }, [])

  return (
    <div className="min-h-screen bg-slate-900 text-white flex flex-col items-center justify-center gap-4">
      <h1 className="text-4xl font-bold">AI Resume Assistant</h1>
      <p className="text-lg">
        Backend status:{' '}
        <span className="font-mono text-emerald-400">{status}</span>
      </p>
    </div>
  )
}

export default App